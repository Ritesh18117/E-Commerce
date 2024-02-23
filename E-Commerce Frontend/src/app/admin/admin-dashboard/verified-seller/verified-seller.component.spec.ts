import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifiedSellerComponent } from './verified-seller.component';

describe('VerifiedSellerComponent', () => {
  let component: VerifiedSellerComponent;
  let fixture: ComponentFixture<VerifiedSellerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerifiedSellerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VerifiedSellerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
